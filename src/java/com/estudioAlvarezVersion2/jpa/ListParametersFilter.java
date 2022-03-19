/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.estudioAlvarezVersion2.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 *
 * @author juanp
 */
public class ListParametersFilter {
    
    List<Predicate<Expediente>> filters = new ArrayList<>();
    
    public void addFilter(Predicate<Expediente> filter)
    {
        filters.add(filter);
    }
    
    public Predicate<Expediente> getFilter()
    {
        return filters.stream().reduce(Predicate::and).orElse(p -> true);
    }

    public Predicate<? super Expediente> getMultipleFilter() {
        return (Predicate<? super Expediente>) filters;
    }
    
}
