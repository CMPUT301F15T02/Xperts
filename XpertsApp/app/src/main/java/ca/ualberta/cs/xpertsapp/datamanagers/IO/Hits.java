package ca.ualberta.cs.xpertsapp.datamanagers.IO;

import java.util.List;


public class Hits<T> {
	private int total;
	private float max_score;
	private List<SearchHit<T>> hits; // has to be exact "hits" for elastic search to work

	public Hits() {}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public float getMax_score() {
		return max_score;
	}

	public void setMax_score(float max_score) {
		this.max_score = max_score;
	}

	public List<SearchHit<T>> getHits() {
		return hits;
	}

	public void setServiceSearchHits(List<SearchHit<T>> searchHits) {
		this.hits = hits;
	}

	@Override
	public String toString() {
		return "[total=" + total + ", max_score=" + max_score + ", hits="
				+ hits + "]";
	}
}