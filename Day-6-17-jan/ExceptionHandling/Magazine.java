public class Magazine {
    
    public Magazine(String title, String publisher, int issueNumber, int publicationYear){
        this.title = title;
        this.publisher = publisher;
        this.issueNumber = issueNumber;
        this.publicationYear = publicationYear;
    }

    private String title;
    private String publisher;
    private int issueNumber;
    private int publicationYear;

    public String getTitle(){
        return this.title;
    }

    public String getPublisher(){
        return this.publisher;
    }

    public int getIssueNumber(){
        return this.issueNumber;
    }

    public int getPublicationYear(){
        return this.publicationYear;
    }

    public void setTitle(String title){
        if(title == null || title.isBlank() ){
            throw new IllegalArgumentException("Title cannot be blank or null");
        }
         this.title = title;
    }

    public void setPublisher(String publisher){

        if( publisher == null || publisher.isBlank()){
            throw new IllegalArgumentException("Publishig cannot be blank or null");

        }
        this.publisher = publisher;
    }

    public void setIssueNumber(int issueNumber){

        if (issueNumber <= 0){
            throw new IllegalArgumentException("The issue number must be greater than 0");
        }
        this.issueNumber = issueNumber;
    }

    public void setPublicationYear(int publicationYear){
        this.publicationYear = publicationYear;
    }

}
