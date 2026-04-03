package com.shopwave.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request body for the PATCH /api/products/{id}/stock endpoint.
 * Delta can be positive (restock) or negative (selling/adjustment).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockUpdateRequest {

    private int delta;
}
