package quanphung.hust.nctnbackend.utils;

public final class DataFilterUtils
{

  private DataFilterUtils()
  {
  }

  public static int resolveSize(Integer size)
  {
    if (size == null || size < 0)
    {
      return DataFilterConstants.Size.DEFAULT_SIZE;
    }
    if (size > DataFilterConstants.Size.MAX_SIZE)
    {
      return DataFilterConstants.Size.MAX_SIZE;
    }

    return size;
  }

  public static int resolvePage(Integer page)
  {
    if (page == null || page < 0)
    {
      return DataFilterConstants.Page.DEFAULT_PAGE;
    }

    return page;
  }

}
