package ca.ualberta.cs.xpertsapp.model.es;

/**
 * FROM: https://github.com/joshua2ua/AndroidElasticSearch
 * @param <T> Type of search hit
 */
public class SearchHit<T> {
	private String index;
	private String type;
	private String id;
	private String version;
	private boolean found;
	private T source;

	public SearchHit() {
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getID() {
		return id;
	}

	public void setID(String id) {
		this.id = id;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public boolean isFound() {
		return found;
	}

	public void setFound(boolean found) {
		this.found = found;
	}

	public T getSource() {
		return source;
	}

	public void setSource(T source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return "SimpleElasticSearchResponse [_index=" + index + ", _type="
				+ type + ", _id=" + id + ", _version=" + version
				+ ", found=" + found + ", _source=" + source + "]";
	}
}