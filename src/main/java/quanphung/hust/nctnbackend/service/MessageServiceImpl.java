package quanphung.hust.nctnbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageSource messageSource;

    @Override
    public String resolveMessage(String key, Object[] params, Locale locale) {
        locale = locale != null ? locale : Locale.getDefault();
        return messageSource.getMessage(key, params, locale);
    }
}
