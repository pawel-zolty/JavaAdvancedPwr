<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
    <body>
      <h2>Offers</h2>
        <table border="1">
          <tr bgcolor="#9acd32">
            <th>Eq Name</th>
            <th>Price</th>
            <th>Address1</th>
            <th>Address2</th>
          </tr>
          <xsl:for-each select="offers/offer">
          <tr>
            <td><xsl:value-of select="equipment/name"/></td>
            <td><xsl:value-of select="price"/></td>
            <td><xsl:value-of select="seller/address/addressLine1"/></td>
            <td><xsl:value-of select="seller/address/addressLine2"/></td>
          </tr>
          </xsl:for-each>
        </table>
      </body>
  </html>
</xsl:template>

</xsl:stylesheet>