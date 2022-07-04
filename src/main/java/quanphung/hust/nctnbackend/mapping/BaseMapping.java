package quanphung.hust.nctnbackend.mapping;

public interface BaseMapping<E,T>
{
  T convertToDto(E e);
  E convertToEntity(T t);
}
