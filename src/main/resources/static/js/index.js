
var galeria=document.querySelector(".clgaleria");
var mante=document.querySelector(".clmantenimiento");
var ele=document.querySelector(".cleliminar");

  galeria.addEventListener("click", ( )=>{
        document.getElementById("studio").setAttribute("style", "display: block;")
        document.getElementById("palette").setAttribute("style", "display: none;")
        document.getElementById("delete").setAttribute("style", "display: none;")
    });
   mante.addEventListener("click", ( )=>{
		document.getElementById("studio").setAttribute("style", "display: none;")
        document.getElementById("palette").setAttribute("style", "display: block;")
        document.getElementById("delete").setAttribute("style", "display: none;")
    });
     ele.addEventListener("click", ( )=>{
        document.getElementById("studio").setAttribute("style", "display: none;")
        document.getElementById("palette").setAttribute("style", "display: none;")
        document.getElementById("delete").setAttribute("style", "display: block;")
    });
    