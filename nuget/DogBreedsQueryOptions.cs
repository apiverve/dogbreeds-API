using System;
using System.Collections.Generic;
using System.Text;
using Newtonsoft.Json;

namespace APIVerve.API.DogBreeds
{
    /// <summary>
    /// Query options for the Dog Breeds API
    /// </summary>
    public class DogBreedsQueryOptions
    {
        /// <summary>
        /// The name of the dog breed to get information about
        /// Example: beagle
        /// </summary>
        [JsonProperty("breed")]
        public string Breed { get; set; }
    }
}
