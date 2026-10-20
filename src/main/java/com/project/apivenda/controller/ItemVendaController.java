package com.project.apivenda.controller;

import com.project.apivenda.service.ItemVendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/item-venda")
@RequiredArgsConstructor
public class ItemVendaController {
    private final ItemVendaService itemVendaService;

}
