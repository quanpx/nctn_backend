package quanphung.hust.nctnbackend.exception;

public class InvalidSortOrderException extends Exception {

  private static final long serialVersionUID = 6174743290215563353L;

  public InvalidSortOrderException() {
    super("Invalid 'sort' order");
  }

}
