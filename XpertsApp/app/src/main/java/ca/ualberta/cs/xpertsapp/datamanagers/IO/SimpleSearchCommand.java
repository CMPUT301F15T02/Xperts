package ca.ualberta.cs.xpertsapp.datamanagers.IO;

// Setup SimpleSearchCommand having same entity as json response
public class SimpleSearchCommand {

	private SimpleSearchQuery query;
		
	public SimpleSearchCommand(String query) {
		this.query = new SimpleSearchQuery(query);
	}

	public SimpleSearchCommand(String query, String[] fields) {
		throw new UnsupportedOperationException("Fields not yet implemented.");
	}

	public SimpleSearchQuery getQuery() {
		return query;
	}

	public void setQuery(SimpleSearchQuery query) {
		this.query = query;
	}

	static class SimpleSearchQuery {
		private SimpleSearchQueryString queryString;
		
		public SimpleSearchQuery(String query) {
			this.queryString = new SimpleSearchQueryString(query);
		}

		public SimpleSearchQueryString getQueryString() {
			return queryString;
		}

		public void setQueryString(SimpleSearchQueryString queryString) {
			this.queryString = queryString;
		}

		static class SimpleSearchQueryString {
			private String query;
			
			public SimpleSearchQueryString(String query) {
				this.query = query;
			}

			public String getQuery() {
				return query;
			}

			public void setQuery(String query) {
				this.query = query;
			}
		}
	}
}
