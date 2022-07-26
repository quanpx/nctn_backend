package quanphung.hust.nctnbackend.service;

import java.util.Locale;

public interface MessageService {
    String resolveMessage(String key, Object[] params, Locale locale);
}
