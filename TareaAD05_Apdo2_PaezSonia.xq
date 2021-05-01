for $a in distinct-values( //prestamo/libro)
        let $c:= count(for $b in /prestamos/prestamo/libro where $b=$a return $b)
        return <prestamo> <libro> {$a}</libro><veces>{$c}</veces></prestamo>
------------------------------
for $libro in /libros/libro[publicacion >2000]
let $a:=$libro/id
let $b:=count(for $b in /prestamos/prestamo/libro
where $b=$a
return $b)
order by $libro/autor
return<libros2000><libro>{$libro}</libro><veces>{$b}</veces></libros2000> 
