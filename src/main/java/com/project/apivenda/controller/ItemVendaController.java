package com.project.apivenda.controller;

import com.project.apivenda.dto.ApiResponse;
import com.project.apivenda.dto.response.ItemVendaResponse;
import com.project.apivenda.service.ItemVendaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/item-venda")
@RequiredArgsConstructor
public class ItemVendaController {
    private final ItemVendaService itemVendaService;

}
