<%@ page import="com.google.appengine.api.users.UserService,com.google.appengine.api.users.UserServiceFactory" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="Image Hosting via Google App Engine">
<meta name="author" content="Darren Britton">
<title>Picture Box - User Help</title>
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">
<!-- Custom CSS -->
<link href="css/style.css" rel="stylesheet">
</head>
<body>
<!-- Navigation -->
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
          <div class="jumbotron">
              <h1>Hello, User!</h1>
              <p>This help page is here to assist you in using Picture Box as a user.
                As a user you can view private and public images and upload your own images, which can be made private or public (default public)/p>
            </div>
            <div class="alert alert-success" role="alert">A <b>Private</b> image is one that can only be seen by users who have logged in.</div>
            <div class="panel panel-primary">
              <div class="panel-heading">
                <h3 class="panel-title">Viewing Public and Private Images</h3>
              </div>
              <div class="panel-body">
                As a user, you may view both private and public images.
                These images are available on the landing page or by clicking on "Picture Box"
                in the navigation bar at the top of the page.
              </div>
          </div>
              <div class="panel panel-warning">
               <div class="panel-heading">
                <h3 class="panel-title">Uploading Images</h3>
              </div>
              <div class="panel-body">
                To upload an image/images, click Upload in the navigation bar 
                (on mobile, click the striped icon in the top right of the page to access the navigation links).
                This will take you to the upload page where you can click browse, and select up to 10 images to upload.
                Please note <b>Only "jpg, gif, png" files are supported</b>. Then click upload to upload these images to
                Picture Box. Uploaded images will always be private.
              </div>
         </div>
             <div class="panel panel-danger">
               <div class="panel-heading">
                <h3 class="panel-title">Modifying Images (Deleting/Changing Visibility)</h3>
              </div>
              <div class="panel-body">
                To Modify an image, click My Images in the navigation bar.
                This will take you to the a page which displays all the images you have uploaded.
                Navigate to the image you wish to modify and click the options button to the right of it.
                Delete will remove the image from Picture Box and Make Public/Private will change the visibility of the image. 
              </div>
         </div>
              <!-- Footer -->
            <footer>
                <div class="row">
                    <div class="col-lg-12">
                        <p>
                             Copyright &copy; Darren Britton 2014
                        </p>
                    </div>
                </div>
            </footer>
        </div>
        <!-- /.container -->
        <!-- jQuery -->
        <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
        <!-- Bootstrap Core JavaScript -->
        <script src="http://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.1/js/bootstrap.min.js"></script>
        </body>
        </html>