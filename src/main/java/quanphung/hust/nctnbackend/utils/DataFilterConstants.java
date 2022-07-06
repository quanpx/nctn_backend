package quanphung.hust.nctnbackend.utils;

public final class DataFilterConstants
{

  private DataFilterConstants()
  {
  }

  public static final class Page
  {
    private Page()
    {
    }

    public static final int DEFAULT_PAGE = 0;
  }

  public static final class Size
  {

    private Size()
    {
    }

    public static final int DEFAULT_SIZE = 10;

    public static final int MAX_SIZE = 50;

    public static final int BIG_MAX_SIZE = 1000;

  }

  public static final class Sorting
  {

    private Sorting()
    {
    }

    public static final String ASC = "ascend";

    public static final String DESC = "descend";

  }
}
