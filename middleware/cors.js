module.exports = (req,res,next)=>{
  var origin = req.get('origin');
  if(!origin) origin="*";
  res.header("Access-Control-Allow-Origin",origin);
  res.header("Access-Control-Allow-Headers","Content-Type,x-requested-with"); 
  res.header('Access-Control-Allow-Credentials','true');
  res.header("Access-Control-Allow-Methods","DELETE,PUT,POST,GET,OPTIONS");
  next();
};