package ru.kravchenko.designer.service;

import org.springframework.stereotype.Service;
import ru.kravchenko.designer.api.IBootstrapService;

@Service
public class BootstrapService implements IBootstrapService {

    @Override
    public void init() {
        System.out.println("This is BootstrapService");
    }

}
