package com.haengsin.church.board.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/api/v1/boards")
class BoardController {

    @GetMapping
    fun test() = "";
}