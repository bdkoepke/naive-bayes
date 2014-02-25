package pw.swordfish;

import java.util.Date;
import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * @author brandon
 */
public class GoogleRssEntry {

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
	private Date published;

	/**
	 * Get the value of published
	 *
	 * @return the value of published
	 */
	public Date getPublished() {
		return published;
	}

	/**
	 * Set the value of published
	 *
	 * @param published new value of published
	 */
	public void setPublished(Date published) {
		this.published = published;
	}
	private Date updated;

	/**
	 * Get the value of updated
	 *
	 * @return the value of updated
	 */
	public Date getUpdated() {
		return updated;
	}

	/**
	 * Set the value of updated
	 *
	 * @param updated new value of updated
	 */
	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
