package scenario_creation_package;

import java.util.Objects;

public class Scenario 
{
	public String name;
	public int size;
	public int survivors;
	public int clusters;
	public String description;
	public Asset[] assets;
	
	/**
	 * Constructor for a scenario
	 * @param name is the name of the scenario
	 * @param size is the value of the x * x grid
	 * @param clusters is the amount of groups that shall be placed on the grid
	 * @param survivors is the amount of survivors. Must be larger than the clusters
	 * @param description is the description of the scenario
	 * @param assets is an array of all assets available in the scenario
	 */
	public Scenario(String name, int size, int clusters, int survivors, String description, Asset[] assets)
	{
		this.name = name;
        this.size = size;
        this.clusters = clusters;
        this.survivors = survivors;
        this.description = description;
        this.assets = assets;
	}
	
	/**
	 * This constructor is used for deep copying the given scenario.
	 * @param scenario scenario to deep copy
	 */
	public Scenario(Scenario scenario)
	{
		this.name = scenario.name;
        this.size = scenario.size;
        this.clusters = scenario.clusters;
        this.survivors = scenario.survivors;
        this.description = scenario.description;
        this.assets = new Asset[scenario.assets.length];
        for (int i = 0; i < scenario.assets.length; i++) 
        {
            this.assets[i] = scenario.assets[i].deepCopy(); 
        }
	}
	
	/**
	 *	@return returns if given object is equal to this one by values
	 */
	@Override
	public boolean equals(Object obj) 
	{
	    if (this == obj) return true;
	    if (obj == null || getClass() != obj.getClass()) return false;
	    Scenario scenario = (Scenario) obj;
	    return Objects.equals(name, scenario.name) && 
	           Objects.equals(description, scenario.description); 
	}

	/**
	 * @return returns a hash code of this functions name and description
	 */
	@Override
	public int hashCode() {
	    return Objects.hash(name, description); 
	}
}
