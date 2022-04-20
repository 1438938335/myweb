package com.wyy.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PagedResult<T>
{
    private PageVO pageVO;

    private List<T> result;

    public PagedResult()
    {}

    public PagedResult(PageVO pageVO, List<T> result)
    {
        this.pageVO = pageVO;
        this.result = result;
    }

    public static <T> PagedResult<T> defaultPagedResult()
    {
        PageVO pageVO = new PageVO();
        pageVO.setCurPage(1);

        return new PagedResult<>(new PageVO(), new ArrayList<>(0));
    }
}
