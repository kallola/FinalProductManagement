package com.hcl.product.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductSearchCriteria {
    String partname;
    String type;
    double min;
    double max;




}
