<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
<xsl:output method="html" indent="no"/>
	<xsl:template match="/">
		<html>
			<h1>Produktkatalog</h1>
			<xsl:apply-templates select="//HEADER"/>
			<div class="alleArtikel">
				<xsl:apply-templates select="//ARTICLE"/>		
			</div>
		</html>
	</xsl:template>
	
	<xsl:template match="//HEADER">
		<div class="header">
			<p><xsl:value-of select="//HEADER/CATALOG/LANGUAGE"></xsl:value-of></p>	
			<p><xsl:value-of select="//HEADER/CATALOG/CATALOG_ID"></xsl:value-of></p>	
			<p><xsl:value-of select="//HEADER/CATALOG/CATALOG_VERSION"></xsl:value-of></p>
			<p><xsl:value-of select="//HEADER/CATALOG/CATALOG_NAME"></xsl:value-of></p>
			<h3><xsl:value-of select="//HEADER/SUPPLIER/SUPPLIER_NAME"></xsl:value-of></h3>
		</div>	
	</xsl:template>
	
	<xsl:template match="ARTICLE">
		<p><xsl:value-of select="current()/SUPPLIER_AID"></xsl:value-of></p>
		<p><xsl:value-of select="current()/ARTICLE_DETAILS/DESCRIPTION_SHORT"></xsl:value-of></p>
		<p><xsl:value-of select="current()/ARTICLE_DETAILS/DESCRIPTION_LONG"></xsl:value-of></p>
		<p><xsl:value-of select="current()/ARTICLE_ORDER_DETAILS/ORDER_UNIT"></xsl:value-of></p>
		<p><xsl:value-of select="current()/ARTICLE_ORDER_DETAILS/ORDER_UNIT"></xsl:value-of></p>
		<p><xsl:value-of select="current()/ARTICLE_ORDER_DETAILS/CONTENT_UNIT"></xsl:value-of></p>
		<p><xsl:value-of select="current()/ARTICLE_ORDER_DETAILS/NO_CU_PER_OU"></xsl:value-of></p>
		<p><xsl:value-of select="current()/ARTICLE_PRICE_DETAILS/ARTICLE_PRICE/PRICE_AMOUNT"></xsl:value-of></p>
		<p><xsl:value-of select="current()/ARTICLE_PRICE_DETAILS/ARTICLE_PRICE/PRICE_CURRENCY"></xsl:value-of></p>
		<p><xsl:value-of select="current()/ARTICLE_PRICE_DETAILS/ARTICLE_PRICE/TAX"></xsl:value-of></p>
		<p><xsl:value-of select="current()/ARTICLE_PRICE_DETAILS/ARTICLE_PRICE/TERRITORY"></xsl:value-of></p>
		<p><xsl:value-of select="current()/ARTICLE_PRICE_DETAILS/ARTICLE_PRICE/TERRITORY"></xsl:value-of></p>
	</xsl:template>
	
</xsl:stylesheet>