package org.ecommerce;
import org.ecommerce.controller.ProductoController;
import org.ecommerce.controller.PedidoController;
import org.ecommerce.domain.Item;
import org.ecommerce.domain.Pedido;
import org.ecommerce.domain.Producto;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

// SIMULO EL FRONT
public class Main {
    // Atributos
    private static final Scanner scanner = new Scanner(System.in);
    private static final ProductoController productoController = new ProductoController();
    private static final PedidoController pedidoController = new PedidoController();

    static boolean ejecucion = true;

    // Punto de entrada
    public static void main(String[] args) {
        mostrarMenu();
    }

    private static void mostrarMenu(){
        while (ejecucion){
            System.out.println(
                    "\n======= MENU PRINCIPAL =======\n" +
                            "| 1) Agregar Producto         |\n" +
                            "| 2) Listar Productos         |\n" +
                            "| 3) Buscar Producto          |\n" +
                            "| 4) Actualizar Producto      |\n" +
                            "| 5) Eliminar Producto        |\n" +
                            "|-----------------------------|\n" +
                            "| 6) Crear Pedido             |\n" +
                            "| 7) Agregar Item a Pedido    |\n" +
                            "| 8) Confirmar Pedido         |\n" +
                            "| 9) Listar Pedidos           |\n" +
                            "|-----------------------------|\n" +
                            "| 0) Salir                    |\n" +
                            "==============================\n" +
                            "Seleccione una opción: "
            );

            int opcion = leerEntero();

            switch (opcion){
                case 1:
                    agregarProducto();
                    break;
                case 2:
                    listarProductos();
                    break;
                case 3:
                    consultarProducto();
                    break;
                case 4:
                    actualizarProducto();
                    break;
                case 5:
                    eliminarProducto();
                    break;
                case 6:
                    agregarPedido();
                    break;
                case 7:
                    agregarItem();
                    break;
                case 8:
                    confirmarPedido();
                    break;
                case 9:
                    listarPedidos();
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    ejecucion = false;
                    return;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    // Intento simular fetch con los try
    private static void agregarProducto(){
        try {
            System.out.println("\nIngrese nombre del producto: ");
            String nombreProducto = scanner.nextLine().trim();
            System.out.println("\nIngrese el precio del producto: ");
            float precioProducto = Float.parseFloat(scanner.nextLine().trim());
            System.out.println("\nIngrese la cantidad el stock: ");
            int stockProducto = Integer.parseInt(scanner.nextLine().trim());
            productoController.crearProducto(nombreProducto, precioProducto, stockProducto);
            System.out.println("\nProducto creado\n");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void listarProductos(){
        try {
            List<Producto> productos = productoController.listarProductos();
            if (productos.isEmpty()) {
                System.out.println("\nNo hay productos cargados.\n");
                return;
            }
            System.out.println("\n=== Lista de Productos ===");
            for (Producto producto : productos) {
                System.out.println("ID: " + producto.getIdProducto());
                System.out.println("Nombre: " + producto.getNombreProducto());
                System.out.println("Precio: $" + producto.getPrecioProducto());
                System.out.println("Stock: " + producto.getStockProducto());
                System.out.println("-----------------------------");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void consultarProducto(){
        try {
            System.out.print("\nIngrese el ID del producto a consultar: ");
            int idProducto = Integer.parseInt(scanner.nextLine().trim());

            Optional<Producto> productoOpt = productoController.obtenerProductoPorId(idProducto);

            if (productoOpt.isPresent()) {
                Producto producto = productoOpt.get();
                System.out.println("\n=== Detalles del Producto ===");
                System.out.println("ID: " + producto.getIdProducto());
                System.out.println("Nombre: " + producto.getNombreProducto());
                System.out.println("Precio: $" + producto.getPrecioProducto());
                System.out.println("Stock: " + producto.getStockProducto());
            } else {
                System.out.println("\nProducto no encontrado con ID " + idProducto);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un numero valido.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void actualizarProducto() {
        try {
            System.out.print("\nIngrese el ID del producto a actualizar: ");
            int idProducto = Integer.parseInt(scanner.nextLine().trim());
            Optional<Producto> productoOpt = productoController.obtenerProductoPorId(idProducto);
            if (productoOpt.isPresent()) {
                Producto producto = productoOpt.get();
                System.out.println("\n=== Datos actuales del producto ===");
                System.out.println("Nombre: " + producto.getNombreProducto());
                System.out.println("Precio: $" + producto.getPrecioProducto());
                System.out.println("Stock: " + producto.getStockProducto());
                System.out.print("\nNuevo nombre del producto: ");
                String nuevoNombre = scanner.nextLine().trim();
                System.out.print("Nuevo precio del producto: ");
                float nuevoPrecio = Float.parseFloat(scanner.nextLine().trim());
                System.out.print("Nuevo stock del producto: ");
                int nuevoStock = Integer.parseInt(scanner.nextLine().trim());
                productoController.actualizarProducto(idProducto, nuevoNombre, nuevoPrecio, nuevoStock);
                System.out.println("\nProducto actualizado correctamente.");
            } else {
                System.out.println("\nProducto no encontrado con ID " + idProducto);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese valores numericos validos.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void eliminarProducto() {
        try {
            System.out.print("\nIngrese el ID del producto a eliminar: ");
            int idProducto = Integer.parseInt(scanner.nextLine().trim());
            Optional<Producto> productoOpt = productoController.obtenerProductoPorId(idProducto);
            if (productoOpt.isPresent()) {
                Producto producto = productoOpt.get();
                System.out.println("\nProducto encontrado:");
                System.out.println("Nombre: " + producto.getNombreProducto());
                System.out.println("Precio: $" + producto.getPrecioProducto());
                System.out.println("Stock: " + producto.getStockProducto());
                System.out.print("¿Está seguro que desea eliminar este producto? (s/n): ");
                String confirmacion = scanner.nextLine().trim().toLowerCase();
                if (confirmacion.equals("s")) {
                    productoController.eliminarProducto(idProducto);
                    System.out.println("\nProducto eliminado correctamente.");
                } else {
                    System.out.println("\nOperación cancelada.");
                }
            } else {
                System.out.println("\nProducto no encontrado con ID " + idProducto);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Ingrese un ID válido.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void agregarPedido(){
        try {
            pedidoController.crearPedido();
            System.out.println("\nPedido creado\n");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void agregarItem() {
        try {
            System.out.print("\nIngrese el ID del pedido: ");
            int idPedido = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Ingrese el ID del producto: ");
            int idProducto = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Ingrese la cantidad: ");
            int cantidad = Integer.parseInt(scanner.nextLine().trim());
            pedidoController.agregarItem(idPedido, idProducto, cantidad);
            System.out.println("\nItem agregado al pedido exitosamente.\n");
        } catch (NumberFormatException e) {
            System.out.println("Error: entrada numerica invalida.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void confirmarPedido(){
        try {
            System.out.print("\nIngrese el ID del pedido a confirmar: ");
            int idPedido = Integer.parseInt(scanner.nextLine().trim());
            pedidoController.confirmarPedido(idPedido);
            System.out.println("\nPedido confirmado exitosamente.\n");
        } catch (NumberFormatException e) {
            System.out.println("Error: entrada numerica invalida.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Se que podria devolver una lista con todos los string y no tener que importar las clases y bajar el acoplamiento
    private static void listarPedidos() {
        try {
            List<Pedido> pedidos = pedidoController.listarPedidos();
            if (pedidos.isEmpty()) {
                System.out.println("\nNo hay pedidos registrados.\n");
            } else {
                System.out.println("\n=== Lista de Pedidos ===");
                for (Pedido pedido : pedidos) {
                    System.out.println("\n#############################");
                    System.out.println("ID: " + pedido.getIdPedido());
                    System.out.println("Fecha: " + pedido.getFechaPedido());
                    System.out.println("Confirmado: " + (pedido.isConfirmado() ? "Si" : "No"));
                    System.out.println("Items:");
                    for (Item item : pedido.getItems()) {
                        Producto producto = item.getProducto();
                        System.out.println("  ----------------------");
                        System.out.println("  Producto: " + producto.getNombreProducto());
                        System.out.println("  Cantidad: " + item.getCantidadProducto());
                        System.out.println("  Precio unitario: $" + producto.getPrecioProducto());
                    }
                    System.out.println("Total: $" + pedido.getTotal());
                    System.out.println("#############################\n");
                }
            }
        } catch (Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Lectura de entero para el Menu
    private static int leerEntero() {
        while (true) {
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Entrada no válida. Ingrese un número: ");
            }
        }
    }
}