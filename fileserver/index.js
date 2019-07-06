require('dotenv').config()
const express = require('express');
const fs = require('fs');
const bodyParser = require('body-parser');
const pathObj = require('path');
const multer = require('multer')

const listenPort = process.env.FILE_SERVER_PORT;

const protocal = process.env.MIDDLEWARE_PROTOCAL;
const domain = process.env.MIDDLEWARE_DOMAIN;
const port = process.env.MIDDLEWARE_PORT;

const dest = process.env.STORE_DIR;
const limit = process.env.SIZE_LIMIT;

const urlPrefix = `${protocal}://${domain}:${port}/imagesUpload/files/`;
const upload = multer({dest})
const server = express();

server.use(bodyParser.json({limit}));
server.use(bodyParser.urlencoded({limit, extended: true}));

server.post('/upload',upload.single('file'),(req,res)=>{
    if(!req.file) { //没有文件
        res.json({msg:'没有选择文件！',status:"failed"}).end();
    } else {
        var file = req.file;
        console.log('====================================================');
        console.log('fieldname: ' + file.fieldname);
        console.log('originalname: ' + file.originalname);
        console.log('encoding: ' + file.encoding);
        console.log('mimetype: ' + file.mimetype);
        console.log('size: ' + (file.size / 1024).toFixed(2) + 'KB');
        console.log('destination: ' + file.destination);
        console.log('filename: ' + file.filename);
        console.log('path: ' + file.path);

        var fileObj = pathObj.parse(file.originalname);
        newPath = file.path + fileObj.ext;
        oldPath = file.path;

        fs.rename(oldPath,newPath,(err)=>{
            if(err) {
                console.log(err);
                res.json({
                  status:"failed",
                  msg:'internal error'
                }).end();
            } else {
                res.json({
                    status:"done",
                    name:file.originalname,
                    url:urlPrefix+file.filename + fileObj.ext,
                }).end();
            }
        })
    }
});

server.use('/files', express.static(dest));

server.get('/',(req,res)=>{
    res.json({msg:"fileserver start success!"})
})

server.listen(listenPort);
console.log('file server starting...');

//console.log(urlPrefix,limit,dest)


