package quanphung.hust.nctnbackend.repository.orderutils;

import java.util.Arrays;
import java.util.List;

import com.querydsl.core.types.OrderSpecifier;
import quanphung.hust.nctnbackend.domain.InitializationInfo;
import quanphung.hust.nctnbackend.domain.LotInfo;
import quanphung.hust.nctnbackend.domain.QUserAuction;
import quanphung.hust.nctnbackend.exception.InvalidSortColumnException;
import quanphung.hust.nctnbackend.exception.InvalidSortOrderException;
import quanphung.hust.nctnbackend.utils.DataFilterConstants;
import quanphung.hust.nctnbackend.utils.OrderUtils;

public class UserAuctionOrderUtils
{
  private static final List<String> SORTABLE_COLUMNS = Arrays.asList(InitializationInfo.ColumnName.CREATED_DATE,LotInfo.ColumnName.INIT_PRICE);

  private UserAuctionOrderUtils()
  {

  }

  public static OrderSpecifier<String>[] createOrderBy(String[] orderByColumns)
    throws InvalidSortOrderException, InvalidSortColumnException
  {
    String entityName = QUserAuction.userAuction.getMetadata().getName();
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
