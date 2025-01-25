package scenario_creation_package;

public class Step
{
    public int x;
    public int y;
    public Boolean noUse;
    
    /**
     * @param x x range to go
     * @param y y range to go
     * @param noUse marks if step is functional
     */
    public Step(int x, int y, Boolean noUse) 
    {
        this.x = x;
        this.y = y;
        this.noUse = noUse;
    }
}
