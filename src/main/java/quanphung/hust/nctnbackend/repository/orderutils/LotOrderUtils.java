package quanphung.hust.nctnbackend.repository.orderutils;

import java.util.Collections;
import java.util.List;

import com.querydsl.core.types.OrderSpecifier;
import quanphung.hust.nctnbackend.domain.InitializationInfo;
import quanphung.hust.nctnbackend.domain.QAuctionSession;
import quanphung.hust.nctnbackend.domain.QLotInfo;
import quanphung.hust.nctnbackend.exception.InvalidSortColumnException;
import quanphung.hust.nctnbackend.exception.InvalidSortOrderException;
import quanphung.hust.nctnbackend.utils.DataFilterConstants;
import quanphung.hust.nctnbackend.utils.OrderUtils;

public class LotOrderUtils
{
  private static final List<String> SORTABLE_COLUMNS = Collections
    .singletonList(InitializationInfo.ColumnName.CREATED_DATE);

  private LotOrderUtils()
  {

  }

  public static OrderSpecifier<String>[] createOrderBy(String[] orderByColumns)
    throws InvalidSortOrderException, InvalidSortColumnException
  {
    String entityName = QLotInfo.lotInfo.getMetadata().getName();
    if (orderByColumns == null || orderByColumns.length == 0)
    {
      orderByColumns = new String[] {
        InitializationInfo.ColumnName.CREATED_DATE + ":" + DataFilterConstants.Sorting.DESC
      };
      return OrderUtils.createOrderBy(entityName, orderByColumns, SORTABLE_COLUMNS);
    }

    return OrderUtils.createOrderBy(entityName, orderByColumns, SORTABLE_COLUMNS);
  }

}
