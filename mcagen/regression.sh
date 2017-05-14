#/bin/bash
echo 'place the feature file in bdd repo'
sh ./upload_local_mpd.sh
sh ./upload_local_pact.sh
sh ./local_setting.sh
sh ./regression_result.sh
sh ./regression_report.sh