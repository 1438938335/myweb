package com.wyy.vo;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PageVO
{
    private static final int MAX_PAGESIZE = 2000;

    private int curPage = 1;

    private int pageSize = 15;

    private int totalRows;

    private int resultMode;

    private Integer getStartIndex()
    {
        return (curPage - 1) * pageSize + 1;
    }

    private Integer getMysqlStartIndex()
    {
        return (curPage - 1) * pageSize;
    }

    public Integer getTotalPages()
    {
        if (pageSize <= 0)
        {
            return 0;
        }
        return totalRows % pageSize == 0 ? totalRows / pageSize : totalRows /pageSize + 1;
    }

    public Integer getEndIndex()
    {
        return curPage * pageSize;
    }

    public int getCurPage()
    {
        return curPage;
    }

    public void setCurPage(int curPage)
    {
        if (curPage <= 0)
        {
            this.curPage = 1;
        }
        else
        {
            this.curPage = curPage;
        }
    }

    public int getPageSize()
    {
        return pageSize;
    }

    public void setPageSize(int pageSize)
    {
        if (pageSize > MAX_PAGESIZE)
        {
            this.pageSize = -1;
            return;
        }
        this.pageSize = pageSize;
    }

    public int getResultMode()
    {
        return resultMode;
    }

    public void setResultMode(Integer resultMode)
    {
        this.resultMode = resultMode;
    }

    public int getTotalRows()
    {
        return totalRows;
    }

    public void setTotalRows(int totalRows)
    {
        this.totalRows = totalRows;
    }

    public static void main(String[] args)
    {
        log.info("hello");
    }
}
