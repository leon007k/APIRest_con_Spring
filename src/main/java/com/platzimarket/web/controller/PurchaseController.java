package com.platzimarket.web.controller;

import com.platzimarket.domain.Purchase;
import com.platzimarket.domain.service.PurchaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
@Tag(name = "Puchases marketPlatzi", description = "Purchases API")
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Operation(summary = "Get All purchases")
    @ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", array = @ArraySchema(
                    schema = @Schema(implementation = Purchase.class)
            ))})
    @GetMapping
    public ResponseEntity<List<Purchase>> getAll(){
        return new ResponseEntity<>(purchaseService.getAll(), HttpStatus.OK);
    }

    @Operation(summary = "Get purchase by client ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = {@Content(mediaType = "application/json", array = @ArraySchema(
                            schema = @Schema(implementation = Purchase.class)))}),
            @ApiResponse(responseCode = "400", description = "invalid id", content = @Content),
            @ApiResponse(responseCode = "404", description = "client not found", content = @Content)
    })
    @GetMapping("/client")
    public ResponseEntity<List<Purchase>> getByClientId(@RequestParam String clientId){
        List<Purchase> purchases = purchaseService.getByClientId(clientId).orElse(null);
        return purchases != null && !purchases.isEmpty() ? new ResponseEntity<>(purchases, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @Operation(summary = "Add purschase")
    /*@ApiResponse(responseCode = "200", description = "Successful operation",
            content = {@Content(mediaType = "application/json", array = @ArraySchema(
                    schema = @Schema(implementation = Purchase.class)
            ))})*/
    @PostMapping()
    public ResponseEntity<Purchase> save(@RequestBody Purchase purchase){
        return new ResponseEntity<>(purchaseService.save(purchase), HttpStatus.CREATED);
    }

}
