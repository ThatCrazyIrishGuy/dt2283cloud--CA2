<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService(); %>
	
<html>
  <head> 
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="Image Hosting via Google App Engine">
  <meta name="author" content="Darren Britton">
  
  <title>
    Picture Box - Image Uploader
  </title>
  
  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
  
  <!-- Optional theme -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
  
  <!-- Photo Uploader CSS -->
  <link href="css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />
  
  <!-- Jquery -->
  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js">
  </script>
  
  <!-- Photo Uploader JS -->
  <script src="js/fileinput.js" type="text/javascript">
  </script>
  
  <!-- Latest compiled and minified JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js">
  </script>
  
  </head>
  <body>
    <div class="container">
      <h2>
        Photo Upload Form
      </h2>
      <form role="form" action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post"
      onSubmit="if(document.getElementById('input-24').value == '') return false;" enctype="multipart/form-data">
        <div class="form-group">
          <div class="form-group">
            <input id="input-24" type="file" multiple="false" name="myFile">
          </div>
           </form>
      </div>
      </body>
  </html>

<script>
$("#input-24").fileinput({
  maxFileSize: 32000,
  allowedFileExtensions: ['jpg', 'gif', 'png'],
});
</script>
</script>
