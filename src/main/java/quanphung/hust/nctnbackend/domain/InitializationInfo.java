package quanphung.hust.nctnbackend.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PUBLIC)
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class InitializationInfo
{

  @CreatedBy
  @Column(name = ColumnName.CREATED_BY, updatable = false)
  private String createdBy;

  @CreatedDate
  @Column(name = ColumnName.CREATED_DATE, updatable = false)
  private Timestamp createdDate;

  public static class ColumnName
  {

    private ColumnName()
    {
    }

    public static final String CREATED_BY = "created_by";
    public static final String CREATED_DATE = "created_date";
  }

}
