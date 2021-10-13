package ajou.withme.locationData.service;

import ajou.withme.locationData.domain.InitSafeZone;
import ajou.withme.locationData.repository.InitSafeZoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InitSafeZoneService {
    private final InitSafeZoneRepository initSafeZoneRepository;

    public List<InitSafeZone> saveInitSafeZone(List<InitSafeZone> safeZones) {
        return initSafeZoneRepository.saveAll(safeZones);
    }
}
