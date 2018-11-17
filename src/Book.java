public class Book extends Resource {
	private String author;
	private String publisher;
	private String ISBN;
	private String language;
	
	public Book (int uniqueID, String title, int year, String author, String publisher, String ISBN, String language) {
		super(uniqueID, title, year);
		this.author = author;
		this.publisher = publisher;
		this.ISBN = ISBN;
		this.language = language;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String ISBN) {
		this.ISBN = ISBN;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
