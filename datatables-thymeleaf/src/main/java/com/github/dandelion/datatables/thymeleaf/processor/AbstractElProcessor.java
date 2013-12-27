/*
 * [The "BSD licence"]
 * Copyright (c) 2013 Dandelion
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * 3. Neither the name of Dandelion nor the names of its contributors
 * may be used to endorse or promote products derived from this software
 * without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 * IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 * THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.dandelion.datatables.thymeleaf.processor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.thymeleaf.Arguments;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.dom.Element;
import org.thymeleaf.processor.IElementNameProcessorMatcher;
import org.thymeleaf.processor.ProcessorResult;
import org.thymeleaf.processor.element.AbstractElementProcessor;

import com.github.dandelion.datatables.core.html.HtmlTable;
import com.github.dandelion.datatables.thymeleaf.dialect.DataTablesDialect;

/**
 * <p>Abstract superclass for all Datatables element processors.
 * 
 * @author Thibault Duchateau
 */
public abstract class AbstractElProcessor extends AbstractElementProcessor {

	protected HtmlTable table;
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	
    public AbstractElProcessor(IElementNameProcessorMatcher matcher) {
		super(matcher);
	}

    @Override
	protected ProcessorResult processElement(Arguments arguments, Element element) {
    	
    	request = ((IWebContext) arguments.getContext()).getHttpServletRequest();
    	response = ((IWebContext) arguments.getContext()).getHttpServletResponse();
    	table = (HtmlTable) getFromRequest(DataTablesDialect.INTERNAL_BEAN_TABLE);
    	
    	ProcessorResult processorResult = doProcessElement(arguments, element);
    	return processorResult;
    }

    @Override
    public abstract int getPrecedence();

    /**
     * Process the Attribute
     *
     * @param arguments Thymeleaf arguments
     * @param element Element of the attribute
     * @param attributeName attribute name
     * @return result of process
     */
    protected abstract ProcessorResult doProcessElement(Arguments arguments, Element element);
    
    public void storeInRequest(String referenceName, Object value){
    	request.setAttribute(referenceName, value);
    }
    
    public Object getFromRequest(String referenceName){
    	return request.getAttribute(referenceName);
    }
}