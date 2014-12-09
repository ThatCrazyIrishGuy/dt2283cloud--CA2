<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory,com.google.appengine.api.blobstore.BlobstoreService,com.google.appengine.api.users.UserService,com.google.appengine.api.users.UserServiceFactory" %>
<%BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService(); %>
<html>
  <head> 
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="description" content="Image Hosting via Google App Engine">
  <meta name="author" content="Darren Britton">
  <img src="img/uploadBackground.png" id="bg" alt="MISSING IMAGE">
  <title>Picture Box - Image Uploader</title>
  
  <!-- Base CSS Styling -->
  <link rel="stylesheet" href="css/style.css">
  
  <!-- Latest compiled and minified CSS -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
  
  <!-- Optional theme -->
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
  
  <!-- Photo Uploader CSS -->
  <link href="css/fileinput.min.css" media="all" rel="stylesheet" type="text/css" />

  <!-- Jquery -->
  <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
  <!-- Photo Uploader JS -->
  <script src="js/fileinput.js" type="text/javascript"></script>
  <!-- Latest compiled and minified JavaScript -->
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="/getblobs">Picture Box</a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <% UserService userService = UserServiceFactory.getUserService(); %>
            <ul class="nav navbar-nav">
                <% if(userService.isUserLoggedIn()){ %>
                    <li>
                    <a href="/loginlogout">Logout</a>
                    </li>
                    <li>
                    <a href="/upload.jsp">Upload</a>
                    </li>
                    <li>
                    <a href="/getuserblobs">My Images</a>
                    </li>
                    <% if(userService.isUserAdmin()){ %>
                        <li>
                        <a href="/getadminblobs">Admin Panel</a>
                        </li>
                        <li>
                        <a href="/adminHelp.jsp">Help</a>
                        </li>
                    <%} else {%>
                        <li>
                        <a href="/userHelp.jsp">Help</a>
                        </li>
                <%}} else {%>
                    <li>
                    <a href="/loginlogout">Login</a>
                    </li>
                    <li>
                    <a href="/guestHelp.jsp">Help</a>
                    </li>
                <%}%>
            </ul>
        </div>
        <!-- /.navbar-collapse -->
    </div>
    <!-- /.container -->
    </nav>
    <div class="container">
    	<div class="row">
        	<div class="col-md-12">
      <h2>
        Photo Upload Form
      </h2>
      <form role="form" action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post"
      onSubmit="if(document.getElementById('input-24').value == '') return false;" enctype="multipart/form-data">
        <div class="form-group">
          <div class="form-group">
            <input id="input-24" type="file" name="myFile" multiple>
            </div>
            </div>
          </div>
           </form>
      </div>
      </body>
  </html>

<script>
$("#input-24").fileinput({
  maxFileSize: 32000,
  maxFileCount: 10,
  allowedFileExtensions: ['jpg', 'gif', 'png'],
});
</script>
</script>
