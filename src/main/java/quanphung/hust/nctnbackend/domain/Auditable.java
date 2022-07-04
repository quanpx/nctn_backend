package quanphung.hust.nctnbackend.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class Auditable extends InitializationInfo
{

  public static final class ColumnName
  {
    private ColumnName()
    {
    }

    public static final String UPDATED_BY = "updated_by";
    public static final String LAST_UPDATED_AT = "last_modified_date";

  }

  @LastModifiedBy
  @Column(name = ColumnName.UPDATED_BY)
  private String updatedBy;

  @LastModifiedDate
  @Column(name = ColumnName.LAST_UPDATED_AT)
  private Timestamp lastModifiedDate;
}
