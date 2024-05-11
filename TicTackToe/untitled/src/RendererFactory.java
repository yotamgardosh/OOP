/**
 * The RendererFactory class is responsible for creating instances of different renderer types.
 * It provides a method to build a renderer based on the specified renderer type.
 * Supported renderer types include "console" and "none."
 *
 * @author Yotam
 */
public class RendererFactory {

    /**
     * Builds a renderer based on the specified renderer type.
     *
     * @param rendererType The type of renderer to be created ("console" or "none").
     * @param size The size parameter required for certain renderer types.
     * @return A renderer instance of the specified type, or null if the type is not recognized.
     */
    Renderer buildRenderer(String rendererType, int size){
        Renderer renderer = null;
        switch (rendererType){
            case "console":
                renderer = new ConsoleRenderer(size);
                break;
            case "none":
                renderer = new VoidRenderer();
                break;
        }
        return renderer;
    }
}
