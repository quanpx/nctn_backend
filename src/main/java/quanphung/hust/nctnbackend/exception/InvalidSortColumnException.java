package quanphung.hust.nctnbackend.exception;

public class InvalidSortColumnException extends Exception {

  private static final long serialVersionUID = 6174743290215563353L;

  public InvalidSortColumnException() {
    super("Invalid 'sort' column");
  }

}
