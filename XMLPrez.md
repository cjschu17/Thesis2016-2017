# XML (*eXtensible* Markup Language) structure

----

## Well-formed XML

- things are ordered by hierarchy of elements - Chinese Box
    - spring from a single root element
- *elements*: `<div>` `<text>` `<p>` `<l>`
- *attributes* provide metadata about your structure or text contents
    - `n` `type` `val` 

----

## Valid XML

- can define "rules" in a schema
- TEI is one schema (rule set)

----

## TEI

- is too big
- we'll use a tiny (but sufficient) subset
- we'll *validate* our texts

----

## Xml Copy Editor

- can create new TEI doc in your VM with Xml Copy Editor
- edit one fragmentary tile on the big screen?
- mark break at edge of word with `<gap/>`

----
----
----
----
----

# General introduction to XML #

----

## Background ##

Scholarly editions must be planned to last for the indefinite future.  Digital scholarly editions differ from print editions primarily in the fact that any use of a digital text — whether for the most sophisticated analysis, formatting for interactive reading, or simply for generating a hard copy — is mediated by a machine.  To support the flexibility of a digital environment, the editor  must create an edition that captures the *semantics* of a text, rather than dictating a specific form of presentation that might be appropriate to one use but not another.

The eXtensible Markup Language (XML) allows us do just this.  We can describe the contents of a text in XML, and use other technologies (such as Cascading Style Sheets, or CSS) to define how the contents should be formatted.  We can use an XML document with other software to generate PDFs or other kinds of output.

XML also has the advantage that it is a straightforward, text-only format that can be easily archived and managed in version control systems.  Its syntax is explicitly defined, and can be automatically verified, as you will see.

----

## Well-formed XML ##

All XML documents follow the same basic set of requirements:  documents that meet these requirements are said to be *well formed*.

----

### Elements
*Elements* are the heart of an XML document. Elements describe the document's semantics.  In XML, every element has an explicit beginning and ending. Syntactically, the beginning of the element is given by an element name contained within angle brackets.  The end of an element is indicated by its name preceded by </ and followed by >.  Example: the following well-formed XML consists of a single element called “p”:

    `<p>One paragraph of text contained within an element “p”.</p>`

(Note that angle brackets are *metacharacters* :  if you want to include an angle bracket in the text content of your XML document, you will need to indicate that with a special notation.)

In addition to containing text content, elements may contain other elements.  When they do, they follow a strict rule of containment:  the beginning and end of the interior element must both be contained within the outer element.

`<p>An element “p” containing text and an interior element marked as <unclear>not clearly legible</unclear>.</p>`

All XML documents have a single root element.  Combined with the rule of containment, this means that all well-formed XML documents obey a strictly hierarchical, "Chinese box" organization.

----

### Attributes ##

Attributes provide further information about a specific element. Syntactically, they are placed within the opening tag of an element and have the form `attribute="value"`.  The following example includes an element named `p` with an attribute `n` giving a name or number value for that specific element. Note that the end tag for the element retains the basic form “p” and does not include any attributes.

    `<p n=”2”>One paragraph of text contained within an element “p”.</p>`

XML editors like oXygen or JEdit will use color to distinguish elements, attributes and text contents.  The default highlighting in oXygen is:

- blue for elements
- orange forattributes
- black for text content

----

## Valid XML ##

One of the most important advantages of a markup language is that the structure can be verified automatically by a parser.   When a parser verifies that the syntax of a well-formed XML document follows some defined set of rules, we refer to the process as *validation* and refer to a document that parses successfully as *valid XML*. 

In the ancestor to XML, the Standard Generalized Markup Language (SGML), that structure was defined using a syntax called Document Type Definitions, or DTDs.  You may still see DTDs in use to define the structure of XML documents today, but one of XML's improvements over SGML was that in XML it is possible to use other technological standards to define a document's structure. 

Two widely used standards are XML Schema (a product of the WWW Consortium), and Relax NG.  Relax NG  is itself an XML language:  documents defining an XML structure in Relax NG are XML documents following Relax NG's structure.  If you need to invent your own document types, you would probably do well to use Relax NG to define its schema.

As an editor, you will always want to create documents that can be validated against a schema of some kind.  The schema specifies what elements can be used, and how.

The most widely used vocabulary for scholarly editions of texts follows the guidelines of an international project called the Text Encoding Initiative (TEI).  For more information about editing XML texts following the TEI guidelines, see [this page](xml2.html)


