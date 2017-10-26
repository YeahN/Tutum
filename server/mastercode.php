<?php
  ob_start();
  include('phpqrcode.php');

  $id = $_GET['id'];
  QRcode::png($id);
?>
