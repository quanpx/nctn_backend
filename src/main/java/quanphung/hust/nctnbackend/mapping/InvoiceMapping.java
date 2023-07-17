package quanphung.hust.nctnbackend.mapping;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import quanphung.hust.nctnbackend.domain.AuctionSession;
import quanphung.hust.nctnbackend.domain.Invoice;
import quanphung.hust.nctnbackend.domain.LotInfo;
import quanphung.hust.nctnbackend.dto.AuctionDTO;
import quanphung.hust.nctnbackend.dto.InvoiceDto;
import quanphung.hust.nctnbackend.dto.LotInfoDto;
import quanphung.hust.nctnbackend.repository.LotInfoRepository;

@Component
public class InvoiceMapping implements BaseMapping<Invoice, InvoiceDto>
{

  @Autowired
  private LotMapping lotMapping;

  @Autowired
  private AuctionMapping auctionMapping;

  @Autowired
  private LotInfoRepository lotInfoRepository;


  @Override
  public InvoiceDto convertToDto(Invoice invoice)
  {
    AuctionSession auction = invoice.getSession();
    Set<Long> lotIds = invoice.getLotIds();

    AuctionDTO auctionDTO = auctionMapping.convertToDto(auction);
    List<LotInfo> lotInfoList = lotInfoRepository.findAllById(lotIds);
    Set<LotInfo> lotInfos = new HashSet<>(lotInfoList);
    Set<LotInfoDto> lotInfoDtos = lotInfos.stream()
      .map(lotInfo -> lotMapping.convertToDto(lotInfo))
      .collect(Collectors.toSet());

    String lastModified = invoice.getLastModifiedDate()!=null ? invoice.getLastModifiedDate().toString() : null;
    return InvoiceDto.builder()
      .id(invoice.getId())
      .lots(lotInfoDtos)
      .total(invoice.getTotal())
      .isPaid(invoice.isPaid())
      .customer(invoice.getCustomer())
      .auctionDTO(auctionDTO)
      .time(lastModified)
      .build();
  }

  @Override
  public Invoice convertToEntity(InvoiceDto invoiceDtoto)
  {
    return null;
  }
}
