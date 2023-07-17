package quanphung.hust.nctnbackend.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notification")
public class Notificaiton extends InitializationInfo implements Serializable
{

  private static final long serialVersionUID = -8097134317727418428L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "content")
  private String content;

  @Column(name="from")
  private String from;

  @Column(name = "to")
  private String to;

  @Column(name = "is_read")
  private boolean isRead;

  @Column(name = "type")
  private String type;
}
