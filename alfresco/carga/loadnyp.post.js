var filename = null;
var content = null;
var GN_ID = "";
var GN_NAME = "";
var GN_CREATIONDATE="";
var GN_TITULO="";
var GN_DATE="";
var GN_DOC_ORIG="";
var GN_CADUCITYDATE="";
var GN_VALIDITYDATE="";
var DFecPubliC="";
var CCodPublic="";
var nPublicId="";
var nVersDoc="";
var nnyplistaId="";
var nNumCircular="";


// locate file attributes
for each (field in formdata.fields)
{
  if (field.name == "GN_ID")
  {
    GN_ID = field.value;
  }
   else if (field.name == "GN_CREATIONDATE")
  {
    GN_CREATIONDATE = field.value;
  }
   else if (field.name == "GN_TITULO")
  {
    GN_TITULO = field.value;
  }
   else if (field.name == "GN_DATE")
  {
    GN_DATE = field.value;
  }
  else if (field.name == "GN_DOC_ORIG")
  {
    GN_DOC_ORIG = field.value;
  }
  else if (field.name == "GN_CADUCITYDATE")
  {
    GN_CADUCITYDATE = field.value;
  }
  else if (field.name == "GN_VALIDITYDATE")
  {
    GN_VALIDITYDATE = field.value;
  }
  else if (field.name == "DFecPubliC")
  {
    DFecPubliC = field.value;
  }
  else if (field.name == "CCodPublic")
  {
    CCodPublic = field.value;
  }
  else if (field.name == "nPublicId")
  {
    nPublicId = field.value;
  }
  else if (field.name == "nVersDoc")
  {
    nVersDoc = field.value;
  }
  else if (field.name == "nnyplistaId")
  {
    nnyplistaId = field.value;
  }
  else if (field.name == "nNumCircular")
  {
    nNumCircular = field.value;
  }
  else if (field.name == "file" && field.isFile)
  {
    filename = field.filename;
    content = field.content;
  }
}

// ensure mandatory file attributes have been located
if (filename == undefined || content == undefined)
{
  status.code = 400;
  status.message = "loadnyped file cannot be located in request";
  status.redirect = true;
}
else
{
  // create document in company home for loadnyped file
  var space_to_use = companyhome.childByNamePath("Sites/documentos/documentLibrary/NormasProcedimientos");
  //loadnyp = companyhome.createFile("loadnyp" + companyhome.children.length + "_" + filename) ;
  loadnyp = space_to_use.createFile(filename) ;
  loadnyp.properties.content.write(content);
  loadnyp.properties.encoding = "UTF-8";
  loadnyp.properties.GN_ID = GN_ID;
  loadnyp.properties.GN_NAME = GN_NAME;
  loadnyp.properties["nyp:GN_CREATIONDATE"] = GN_CREATIONDATE;
  loadnyp.properties["nyp:GN_TITULO"] = GN_TITULO;
  loadnyp.properties["nyp:GN_DATE"] = GN_DATE;
  loadnyp.properties["nyp:GN_DOC_ORIG"] = GN_DOC_ORIG;
  loadnyp.properties["nyp:GN_CADUCITYDATE"] = GN_CADUCITYDATE;
  loadnyp.properties["nyp:GN_VALIDITYDATE"] = GN_VALIDITYDATE;
  loadnyp.properties["nyp:DFecPubliC"] = DFecPubliC;
  loadnyp.properties["nyp:CCodPublic"] = CCodPublic;
  loadnyp.properties["nyp:nPublicId"] = nPublicId;
  loadnyp.properties["nyp:nVersDoc"] = nVersDoc;
  loadnyp.properties["nyp:nnyplistaId"] = nnyplistaId;
  loadnyp.properties["nyp:nNumCircular"] = nNumCircular;

   
  loadnyp.save();
 
  // setup model for response template
  model.loadnyp = loadnyp;
}
