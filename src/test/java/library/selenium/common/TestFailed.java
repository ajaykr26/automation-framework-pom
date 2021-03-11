package library.selenium.common;

public class TestFailed extends Exception {

	private static final long serialVersionUID = 1L;
	String message=null;
	public TestFailed()
	{
		super();
	}
	 
	public TestFailed(String message)
	{
	    super(message);
	    this.message = message;
	}
	 
}
