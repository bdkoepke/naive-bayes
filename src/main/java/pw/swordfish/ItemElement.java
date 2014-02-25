package pw.swordfish;

import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author brandon
 */
public class ItemElement {

	private String title;

	/**
	 * Get the value of title
	 *
	 * @return the value of title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Set the value of title
	 *
	 * @param title new value of title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	private String link;

	/**
	 * Get the value of link
	 *
	 * @return the value of link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * Set the value of link
	 *
	 * @param link new value of link
	 */
	public void setLink(String link) {
		this.link = link;
	}
	private String description;

	/**
	 * Get the value of description
	 *
	 * @return the value of description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Set the value of description
	 *
	 * @param description new value of description
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	private String guid;

	/**
	 * Get the value of guid
	 *
	 * @return the value of guid
	 */
	public String getGuid() {
		return guid;
	}

	/**
	 * Set the value of guid
	 *
	 * @param guid new value of guid
	 */
	public void setGuid(String guid) {
		this.guid = guid;
	}
	private Date pubDate;

	/**
	 * Get the value of pubDate
	 *
	 * @return the value of pubDate
	 */
	public Date getPubDate() {
		return pubDate;
	}

	/**
	 * Set the value of pubDate
	 *
	 * @param pubDate new value of pubDate
	 */
	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
