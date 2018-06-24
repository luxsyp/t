package john.snow.rickandmorty.api.cache

import john.snow.dependency.ExecutorFactory
import john.snow.rickandmorty.api.RMDetailService
import john.snow.rickandmorty.db.CharacterDb

class RMDetailCachedService(
        private val executorFactory: ExecutorFactory,
        private val characterDb: CharacterDb,
        private val retrofitDetailService: RMDetailService
) : RMDetailService by retrofitDetailService {


}