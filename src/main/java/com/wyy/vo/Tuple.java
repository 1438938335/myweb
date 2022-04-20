package com.wyy.vo;

import lombok.Data;

@Data
public class Tuple<T,R>
{
    private T first;

    private R second;

    public Tuple(T first, R second)
    {
        this.first = first;
        this.second = second;
    }

    public static <T,R> Tuple<T,R> result(T first, R second)
    {
        return new Tuple<>(first, second);
    }
}
