package pw.swordfish;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.FilenameUtils;
import pw.swordfish.predicate.DatePredicate;
import pw.swordfish.predicate.InPredicate;
import pw.swordfish.query.LoggingQueryable;
import pw.swordfish.query.Queryable;
import pw.swordfish.xml.GoogleRssEntryXmlFormatter;
import pw.swordfish.yql.QueryFactory;
import pw.swordfish.yql.YqlFormatter;
import pw.swordfish.yql.YqlXmlFormatter;

/**
 * @author brandon
 */
public class Program {
	// the date formatter

	private static DateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

	/**
	 * Fetches news articles from google rss entries and parses them with
	 * FiveFilters
	 *
	 * @param baseFileName the base file name for the news article fetcher
	 * @param entries the list of entries
	 * @param fiveFilters the five filters parser to clean the url's
	 * @throws IOException
	 * @throws FormatException
	 */
	private static void fetchNewsArticle(String baseFileName, Iterable<GoogleRssEntry> entries, FiveFilters fiveFilters) throws IOException, FormatException {
		int articleCount = 0;

		String currentFileName = "";

		for (GoogleRssEntry entry : entries) {
			String link = URLEncoder.encode(entry.getLink(), "UTF-8").replace("+", "%20");

			String fileName = dateFormatter.format(entry.getPublished());

			if (!currentFileName.equals(fileName)) {
				articleCount = 0;
				currentFileName = fileName;
			}

			File file = new File(baseFileName + "." + articleCount);
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
				String story = fiveFilters.parse(link);
				writer.write(story);
			}

			articleCount++;
		}
	}

	/**
	 * Fetches news articles from the web
	 *
	 * @param tickerFileMap the file map to fetch news items for
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws FormatException
	 */
	private static void fetchNewsArticles(Map<String, Iterable<GoogleRssEntry>> tickerFileMap) throws IOException, ParserConfigurationException, FormatException {
		// creates a five filters encoder for getting the actual message
		FiveFilters fiveFilters = new FiveFilters();

		for (String ticker : tickerFileMap.keySet()) {
			System.out.println(ticker);

			fetchNewsArticle("news/" + ticker + "/", tickerFileMap.get(ticker), fiveFilters);
		}
	}

	/**
	 * Gets the specified file as a directory, exits if it is not a directory
	 *
	 * @param directory the directory string
	 * @return The directory
	 */
	private static File getDirectory(String directory) {
		File file = new File(directory);
		if (!file.isDirectory()) {
			System.out.println("Not a directory");
			System.exit(-1);
		}
		return file;
	}

	/**
	 * Gets the xml files from the directory
	 *
	 * @param directory the directory to get the xml files for
	 * @return the xml files
	 */
	private static Set<File> getXmlFiles(File directory) {
		// find all the xml files in the directoy
		Set<File> files = new HashSet<>();

		for (File file : directory.listFiles()) {
			if (file.isFile() && file.getName().endsWith(".xml")) {
				files.add(file);
			}
		}
		return files;
	}

	/**
	 * Gets a list of entries for a ticker given a list of files
	 *
	 * @param ticker the ticker to match
	 * @param files the files to check for the ticker
	 * @param xmlFormatter the xml formatter
	 * @return the entries for the ticker
	 * @throws FormatException
	 * @throws FileNotFoundException
	 */
	private static Iterable<GoogleRssEntry> getEntries(String ticker, Iterable<File> files, YqlFormatter<GoogleRssEntry> xmlFormatter) throws FormatException, FileNotFoundException {
		// for each file try to find one that matches the ticker name
		for (File file : files) {
			if (ticker.equals(FilenameUtils.removeExtension(file.getName()))) {
				InputStream stream = new FileInputStream(file);

				// we found it, add it and continue
				return xmlFormatter.getEntities(stream);
			}
		}
		throw new FileNotFoundException();
	}

	/**
	 * Gets the earliest entry
	 *
	 * @param entries the entries to search for the earliest entry
	 * @return
	 */
	private static Date getEarliestEntry(Iterable<GoogleRssEntry> entries) {
		// set the earliest date to right now
		Date earliestDate = new Date();
		for (GoogleRssEntry entry : entries) {
			Date published = entry.getPublished();
			if (published.before(earliestDate)) {
				earliestDate = published;
			}
		}

		return earliestDate;
	}

	/**
	 * Gets the latest date
	 *
	 * @param entries the entries to search for the latest date
	 * @return
	 */
	private static Date getLatestEntry(Iterable<GoogleRssEntry> entries) {
		Date latestDate = new Date(0);
		for (GoogleRssEntry entry : entries) {
			Date published = entry.getPublished();
			if (published.after(latestDate)) {
				latestDate = published;
			}
		}

		return latestDate;
	}

	/**
	 * Gets a training map from training quotes
	 *
	 * @param trainingQuotes the training quotes to build the map for
	 * @return the training map
	 * @throws IOException
	 * @throws FormatException
	 */
	private static Map<Date, Boolean> getMap(Iterable<HistoricStockQuote> quotes) throws IOException, FormatException {
		Map<Date, Boolean> map = new HashMap<>();
		// build the training map
		for (HistoricStockQuote quote : quotes) {
			map.put(quote.getDate(), quote.getClose() > quote.getOpen());
		}

		return map;
	}

	/**
	 * Gets a news map
	 *
	 * @param newsFiles the files
	 * @return the news map for the files
	 * @throws ParseException
	 * @throws IOException
	 */
	private static Map<Date, String> getNewsMap(File[] newsFiles) throws ParseException, IOException {
		Map<Date, String> newsMap = new HashMap<>();
		for (File news : newsFiles) {
			try (BufferedReader reader = new BufferedReader(new FileReader(news))) {
				StringBuilder builder = new StringBuilder();

				while (reader.ready()) {
					builder.append(reader.readLine());
				}

				newsMap.put(dateFormatter.parse(news.getName()), builder.toString());
			}
		}

		return newsMap;
	}

	/**
	 * Updates the given good and bad maps with the training data and news items
	 *
	 * @param newsMap the news map to use
	 * @param trainingMap the training map to use
	 * @param goodMap the good map to update
	 * @param badMap the bad map to update
	 */
	private static void updateMaps(Map<Date, String> newsMap, Map<Date, Boolean> trainingMap, Map<String, Integer> goodMap, Map<String, Integer> badMap) {
		for (Date date : newsMap.keySet()) {
			// TODO: we need to parse the date properly
			if (!trainingMap.containsKey(date)) {
				System.out.println("Could not find key " + date);
			} else {
				boolean direction = trainingMap.get(date);

				String news = newsMap.get(date);

				Map<String, Integer> map;

				if (!direction) {
					map = goodMap;
					System.out.println("down");
				} else {
					map = badMap;
					System.out.println("up");
				}

				for (String token : news.split(" ")) {

					int value = 0;
					if (map.containsKey(token)) {
						value = map.get(token) + 1;
					}

					map.put(token, value);
				}
			}
		}
	}

	private static void runTest(String ticker, Iterable<GoogleRssEntry> entries, Queryable<HistoricStockQuote> queryable, Map<String, Integer> goodMap, Map<String, Integer> badMap) throws IOException, FormatException, ParseException {

			Date latest = getLatestEntry(entries);
			Date earliest = getEarliestEntry(entries);

			// use half of the dateset for training, and the other half for testing
			Date half = getHalfTime(earliest, latest);
			
			// select the test quotes from the second half of the data
			Iterable<HistoricStockQuote> testQuotes = queryable.where(
					new InPredicate("symbol", ticker)
					.and(new DatePredicate(half, DateType.Start)
					.and(new DatePredicate(latest, DateType.End))));

			Map<Date, Boolean> testMap = getMap(testQuotes);
			Map<Date, String> newsMap = getNewsMap(new File("news/" + ticker + "/").listFiles());

			int correct = 0;
			int total = 0;

			for (Date date : newsMap.keySet()) {
				if (!testMap.containsKey(date)) {
					System.out.println("Could not find key " + date);
				} else {
					boolean direction = testMap.get(date);

					String news = newsMap.get(date);

					Map<String, Integer> map;

					long goodCount = 0;
					long totalCount = 0;

					for (String token : news.split(" ")) {
						if (goodMap.containsKey(token) && badMap.containsKey(token)) {
							int count = goodMap.get(token);
							totalCount += count + badMap.get(token);
							goodCount += count;
						}
					}

					if (direction == ((goodCount / totalCount) > 0.5)) {
						System.out.println("Correct");
						correct++;
					} else {
						System.out.println("Incorrect");
					}

					total++;
				}
			}

			System.out.println("Correct: " + ((double) correct / (double) total) + " for ticker: " + ticker);	
	}

	/**
	 * Writes queryable data to csv to illustrate that its possible
	 * @param tickers the tickers to fetch data for and write
	 * @param queryable the queryable to use to fetch the data
	 * @throws IOException 
	 */
	private static void writeCsv(Iterable<String> tickers, Queryable<HistoricStockQuote> queryable) throws IOException, FormatException, ParseException {
				char separator = ',';

		for (String ticker : tickers) {
			try (BufferedWriter writer = new BufferedWriter(new FileWriter(ticker + ".csv"))) {
				StringBuilder csvHeader = new StringBuilder();

				csvHeader.append("Date").append(separator);
				csvHeader.append("Low").append(separator);
				csvHeader.append("High").append(separator);
				csvHeader.append("Open").append(separator);
				csvHeader.append("Close").append(separator);
				csvHeader.append("Volume").append(separator);
				csvHeader.append("AdjustedClose");
				csvHeader.append("\n");

				writer.write(csvHeader.toString());
				
				for (HistoricStockQuote quote : queryable.where(
						new InPredicate("symbol", ticker)
						.and(new DatePredicate("2011-6-01", DateType.Start)
						.and(new DatePredicate("2011-12-01", DateType.End))))) {

					StringBuilder csvBuilder = new StringBuilder();

					csvBuilder.append(quote.getDate()).append(separator);
					csvBuilder.append(quote.getLow()).append(separator);
					csvBuilder.append(quote.getHigh()).append(separator);
					csvBuilder.append(quote.getOpen()).append(separator);
					csvBuilder.append(quote.getClose()).append(separator);
					csvBuilder.append(quote.getVolume()).append(separator);
					csvBuilder.append(quote.getAdjustedClose());
					csvBuilder.append("\n");
					writer.write(csvBuilder.toString());
				}
			}
		}
	}

	/**
	 * Gets the midpoint time between two dates
	 * @param first
	 * @param second
	 * @return 
	 */
	private static Date getHalfTime(Date first, Date second) {
		return new Date(first.getTime() + Math.abs((second.getTime() - first.getTime()) / 2));
	}

	private static void buildTrainingMap(Iterable<String> tickers, Queryable<HistoricStockQuote> queryable, Map<String, Iterable<GoogleRssEntry>> tickerFileMap, Map<String, Integer> goodMap, Map<String, Integer> badMap) throws IOException, FormatException, ParseException {
		// for each ticker, update the training map
		for (String ticker : tickers) {
			Iterable<GoogleRssEntry> entries = tickerFileMap.get(ticker);

			Date latest = getLatestEntry(entries);
			Date earliest = getEarliestEntry(entries);
			Date half = getHalfTime(earliest, latest);

			// use half of the dateset for training, and the other half for testing

			// select the training data from 2011-7-01 to 2011-10-01
			Iterable<HistoricStockQuote> trainingQuotes = queryable.where(
					new InPredicate("symbol", ticker)
					.and(new DatePredicate(earliest, DateType.Start)
					.and(new DatePredicate(half, DateType.End))));

			// get the training map from the specified quotes
			Map<Date, Boolean> trainingMap = getMap(trainingQuotes);
			System.out.println("Got training map for ticker: " + ticker);

			// read all the news files into strings by the date
			Map<Date, String> newsMap = getNewsMap(new File("news/" + ticker + "/").listFiles());
			System.out.println("Got news map for ticker: " + ticker);

			// update the maps with the news and training data
			updateMaps(newsMap, trainingMap, goodMap, badMap);
			System.out.println("Updated map using data from ticker: " + ticker);
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) throws IOException, ParseException, ParserConfigurationException, FormatException {
		// for now we will statically define the tickers
		Set<String> tickers = new HashSet<>();

		// add the tickers from the command line
		for (int i = 1; i < args.length; i++) {
			tickers.add(args[i]);
		}

		// open the directory, and make sure it actually is a directory
		Set<File> xmlFiles = getXmlFiles(getDirectory(args[0]));

		// create a new xml formatter for deserializing the news elements from the google rss xml
		YqlFormatter<GoogleRssEntry> xmlFormatter = new YqlXmlFormatter<>(new GoogleRssEntryXmlFormatter());

		// create a new ticker file map and add the ticker and the 
		// matching news xml file to the map
		Map<String, Iterable<GoogleRssEntry>> tickerFileMap = new HashMap<>();
		for (String ticker : tickers) {
			System.out.println("Loading ticker file map into memory for ticker: " + ticker);
			tickerFileMap.put(ticker, getEntries(ticker, xmlFiles, xmlFormatter));
		}

		// the news articles have already been fetched...
		// uncomment this line to refetch them
		// fetchNewsArticles(tickerFileMap);

		// create a new query factory for getting remote items
		QueryFactory queryFactory = new QueryFactory();

		// create a historic stock quote queryable
		Queryable<HistoricStockQuote> quoteQueryable = new LoggingQueryable<>(queryFactory.createHistoricStockQuoteQueryable());


		// writes the tickers to indpendent csv files
		// writeCsv(tickers, quoteQueryable);

		// words that have a positive effect on the stock price
		Map<String, Integer> goodMap = new HashMap<>();

		// words that have a negative effect on the stock price
		Map<String, Integer> badMap = new HashMap<>();

		// build the training map
		buildTrainingMap(tickers, quoteQueryable, tickerFileMap, goodMap, badMap);

		System.out.println("Maps are loaded into memory");

		// now test the training map on some new data
		for (String ticker : tickers) {
			// get the entries and run the test
			Iterable<GoogleRssEntry> entries = tickerFileMap.get(ticker);

			System.out.println("Running test for ticker: " + ticker);
			runTest(ticker, entries, quoteQueryable, goodMap, badMap);
		}
	}
}
