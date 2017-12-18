using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Text;
using System.Web;
using System.Web.Mvc;

namespace RadioFirebase.Controllers
{
    public class HomeController : Controller
    {
        public ActionResult Index()
        {
            return View();
        }
        
        public ActionResult Edit(string id)
        {
            ViewBag.id = id;
            return View();
        }
        [HttpPost]
        public ActionResult SaveOrUpdate(string id, string RadioName, string RadioDesc, string RadioImg, string RadioCat, string RadioUrl)
        {

            var json = Newtonsoft.Json.JsonConvert.SerializeObject(new
            {
                radyoAd = RadioName,
                radyoDescription = RadioDesc,
                radyoImg = RadioImg,
                radyoKategori = RadioCat,
                radyoUrl = RadioUrl

            });

            var request = WebRequest.CreateHttp("https://internet-radio-app-f63b9.firebaseio.com/Radios/"+id+"/.json");
            request.Method = "PATCH";
            request.ContentType = "application/json";
            var buffer = Encoding.UTF8.GetBytes(json);
            request.ContentLength = buffer.Length;
            request.GetRequestStream().Write(buffer, 0, buffer.Length);
            var response = request.GetResponse();
            json = (new StreamReader(response.GetResponseStream())).ReadToEnd();
            return RedirectToAction("Index", "Home");
        }
    }
}