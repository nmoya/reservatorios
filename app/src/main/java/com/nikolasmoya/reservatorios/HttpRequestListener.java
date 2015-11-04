package com.nikolasmoya.reservatorios;

import org.jsoup.nodes.Document;

public interface HttpRequestListener
{
    void onHttpResponse(Document response);
}
